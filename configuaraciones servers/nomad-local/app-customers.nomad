job "book-store" {
  datacenters = ["dc1"]

  group "servers" {

    count = 1

    network{
        port "http" { }

    }

    task "app-customers" {
      driver = "java"

      artifact {
        source = "https://github.com/JSquinatoa/Programaci-n-Distribuida/releases/download/Version1/app-customers-0.0.1-SNAPSHOT.jar"
        destination = "local/"
        mode = "any"
      }

      config {
        command = "java"
        #jar_path   = "c:/distribuida20262026/app-customers-0.0.1-SNAPSHOT.jar"
        jar_path    = "local/app-customers-0.0.1-SNAPSHOT.jar"
        jvm_options = ["-Xmx1024m", "-Xms128m"]


      args = [
        "-Xmx1024m",
        "-Xms128m",
        "-jar",
        "local/app-customers-0.0.1-SNAPSHOT.jar"
      ]


      }

      env {
        SERVER_PORT = "${NOMAD_PORT_http}"
        SPRING_CLOUD_CONSUL_HOST = "192.168.80.20"
      }

      service {
        provider = "consul"
        name     = "app-customers"
        port     = "http"
      }

    }

  }

}

