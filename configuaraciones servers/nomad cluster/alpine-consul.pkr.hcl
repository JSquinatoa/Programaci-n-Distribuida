packer {
  required_plugins {
    vmware = {
      version = "~> 2"
      source  = "github.com/hashicorp/vmware"
    }
  }
}

variable "consul_version" {
  type    = string
  default = "1.22.7"
}

variable "consul_datacenter" {
  type    = string
  default = "dc1"
}

variable "consul_ip" {
  type    = string
  default = "192.168.73.20"
}

variable "consul_netmask" {
  type    = string
  default = "255.255.255.0"
}

variable "consul_gateway" {
  type    = string
  default = "192.168.73.2"
}

source "vmware-iso" "alpine-consul" {
  iso_url                = "https://dl-cdn.alpinelinux.org/alpine/v3.20/releases/x86_64/alpine-virt-3.20.0-x86_64.iso"
  iso_checksum           = "sha256:27cbb137dbf90c74856fb44f90488066c8bb7aee979365255c63ef925e1de521"
  ssh_username           = "root"
  ssh_password           = "root"
  ssh_timeout            = "30m"
  ssh_port               = 22
  vm_name                = "alpine-consul"
  guest_os_type          = "otherlinux-64"
  memory                 = 1024
  cpus                   = 2
  disk_size              = 10000
  disk_type_id           = "0"
  output_directory       = "output-alpine-consul"
  shutdown_command       = "poweroff"
  network_adapter_type   = "vmxnet3"
  ssh_handshake_attempts = 20
  network                = "nat"
  boot_wait 			 = "60s"
  boot_command = [
    "root<enter><wait>",
    "setup-interfaces -a<enter><wait5>",
#    "ifconfig eth0 ${var.consul_ip} netmask ${var.consul_netmask}<enter><wait>",
#    "route add default gw ${var.consul_gateway}<enter><wait>",
	"udhcpc -i eth0<enter><wait10>",
    "apk update<enter><wait>",
    "apk add openssh<enter><wait>",
    "rc-service sshd start<enter><wait>",
    "echo 'root:root' | chpasswd<enter><wait>",
    "sed -i 's/^#PermitRootLogin.*/PermitRootLogin yes/' /etc/ssh/sshd_config<enter><wait>",
    "echo 'ListenAddress 0.0.0.0' >> /etc/ssh/sshd_config<enter><wait>",
    "rc-service sshd restart<enter><wait>"
  ]
}

build {
  sources = ["source.vmware-iso.alpine-consul"]

  provisioner "shell" {
    inline = [
      "echo 'http://dl-cdn.alpinelinux.org/alpine/v3.20/main' > /etc/apk/repositories",
      "echo 'http://dl-cdn.alpinelinux.org/alpine/v3.20/community' >> /etc/apk/repositories",
      "apk update",
      "apk add curl unzip tar",
      "export FORCE_INSTALL=1",
      "printf 'y\n' | setup-disk -m sys /dev/sda >/tmp/setup-disk.log 2>&1",
      "mkdir -p /mnt /mnt/boot",
      "mount /dev/sda3 /mnt",
      "mount /dev/sda1 /mnt/boot",
      "cp /etc/resolv.conf /mnt/etc/resolv.conf",
      "cat <<'EOF' > /mnt/etc/network/interfaces\nauto lo\niface lo inet loopback\n\nauto eth0\niface eth0 inet static\n    address ${var.consul_ip}\n    netmask ${var.consul_netmask}\n    gateway ${var.consul_gateway}\nEOF",
      "echo 'alpine-consul' > /mnt/etc/hostname",
      "echo 'http://dl-cdn.alpinelinux.org/alpine/v3.20/main' > /mnt/etc/apk/repositories",
      "echo 'http://dl-cdn.alpinelinux.org/alpine/v3.20/community' >> /mnt/etc/apk/repositories",
      "chroot /mnt /bin/sh -lc 'apk update && apk add openssh open-vm-tools unzip wget nano'",
      "mkdir -p /mnt/etc/apk/keys /mnt/tmp/glibc /mnt/lib64",
      "wget -q -O /mnt/etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub",
      "wget -q -O /mnt/tmp/glibc/glibc.apk https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.35-r1/glibc-2.35-r1.apk",
      "chroot /mnt /bin/sh -lc 'apk add --allow-untrusted /tmp/glibc/glibc.apk'",
      "ln -sf /usr/glibc-compat/lib/ld-linux-x86-64.so.2 /mnt/lib64/ld-linux-x86-64.so.2",
      "ln -sf /usr/glibc-compat/lib/libresolv.so.2 /mnt/lib64/libresolv.so.2",
      "mkdir -p /mnt/etc/consul.d /mnt/opt/consul /mnt/usr/local/bin /mnt/tmp/consul_download",
      "curl -L https://releases.hashicorp.com/consul/${var.consul_version}/consul_${var.consul_version}_linux_amd64.zip -o /mnt/tmp/consul_download/consul.zip",
      "chroot /mnt /bin/sh -lc 'unzip -o /tmp/consul_download/consul.zip -d /usr/local/bin'",
      "chmod 0755 /mnt/usr/local/bin/consul",
      "echo 'datacenter = \"${var.consul_datacenter}\"' > /mnt/etc/consul.d/consul.hcl",
      "echo 'data_dir   = \"/opt/consul\"' >> /mnt/etc/consul.d/consul.hcl",
      "echo 'bind_addr  = \"0.0.0.0\"' >> /mnt/etc/consul.d/consul.hcl",
      "echo '' >> /mnt/etc/consul.d/consul.hcl",
      "echo 'server           = true' >> /mnt/etc/consul.d/consul.hcl",
      "echo 'bootstrap_expect = 1' >> /mnt/etc/consul.d/consul.hcl",
      "echo '' >> /mnt/etc/consul.d/consul.hcl",
      "echo 'ui_config {' >> /mnt/etc/consul.d/consul.hcl",
      "echo '  enabled = true' >> /mnt/etc/consul.d/consul.hcl",
      "echo '}' >> /mnt/etc/consul.d/consul.hcl",
      "echo '' >> /mnt/etc/consul.d/consul.hcl",
      "echo 'client_addr = \"0.0.0.0\"' >> /mnt/etc/consul.d/consul.hcl",
      "echo '' >> /mnt/etc/consul.d/consul.hcl",
      "echo 'ports {' >> /mnt/etc/consul.d/consul.hcl",
      "echo '  http  = 8500' >> /mnt/etc/consul.d/consul.hcl",
      "echo '  https = 8501' >> /mnt/etc/consul.d/consul.hcl",
      "echo '  dns   = 8600' >> /mnt/etc/consul.d/consul.hcl",
      "echo '  grpc  = 8502' >> /mnt/etc/consul.d/consul.hcl",
      "echo '}' >> /mnt/etc/consul.d/consul.hcl",
      "chmod 0644 /mnt/etc/consul.d/consul.hcl",
      "echo '#!/sbin/openrc-run' > /mnt/etc/init.d/consul",
      "echo 'name=\"Consul\"' >> /mnt/etc/init.d/consul",
      "echo 'description=\"Consul agent\"' >> /mnt/etc/init.d/consul",
      "echo 'command=\"/usr/local/bin/consul\"' >> /mnt/etc/init.d/consul",
      "echo 'command_args=\"agent -config-dir=/etc/consul.d\"' >> /mnt/etc/init.d/consul",
      "echo 'pidfile=\"/run/consul.pid\"' >> /mnt/etc/init.d/consul",
      "echo 'command_background=true' >> /mnt/etc/init.d/consul",
      "echo '' >> /mnt/etc/init.d/consul",
      "echo 'depend() {' >> /mnt/etc/init.d/consul",
      "echo '  need net' >> /mnt/etc/init.d/consul",
      "echo '  use dns logger' >> /mnt/etc/init.d/consul",
      "echo '}' >> /mnt/etc/init.d/consul",
      "echo '' >> /mnt/etc/init.d/consul",
      "echo 'start_pre() {' >> /mnt/etc/init.d/consul",
      "echo '  checkpath -d -m 0755 /opt/consul' >> /mnt/etc/init.d/consul",
      "echo '}' >> /mnt/etc/init.d/consul",
      "chmod 0755 /mnt/etc/init.d/consul",
      "sed -i 's/^#PermitRootLogin.*/PermitRootLogin yes/' /mnt/etc/ssh/sshd_config",
      "grep -q '^PermitRootLogin yes$' /mnt/etc/ssh/sshd_config || echo 'PermitRootLogin yes' >> /mnt/etc/ssh/sshd_config",
      "grep -q '^ListenAddress 0.0.0.0$' /mnt/etc/ssh/sshd_config || echo 'ListenAddress 0.0.0.0' >> /mnt/etc/ssh/sshd_config",
      "chroot /mnt /bin/sh -lc 'echo root:root | chpasswd'",
      "rm -rf /mnt/tmp/consul_download /mnt/tmp/glibc",
      "chroot /mnt /bin/sh -lc 'rc-update add sshd default'",
      "chroot /mnt /bin/sh -lc 'rc-update add networking default'",
      "chroot /mnt /bin/sh -lc 'rc-update add open-vm-tools default'",
      "chroot /mnt /bin/sh -lc 'rc-update add consul default'",
      "sync"
    ]
  }
}
