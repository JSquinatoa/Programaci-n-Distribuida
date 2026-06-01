
import React from 'react'

type Author = {
  id: number
  name: string
}

type Book = {
  isbn: string
  price: number
  title: string
  authors: Array<Author>
}

interface Props {
  book: Book
}

const BookCard: React.FC<Props> = ({ book }) => {
  // Usamos el ISBN como identificador (no hay campo id en Book)
  const id = book.isbn
  const title = book.title
  const price = book.price
  const isbn = book.isbn
  // Nombre del primer autor, con manejo seguro si no hay autores
  const name = book.authors && book.authors.length > 0 ? book.authors[0].name : 'Desconocido'
  const authors = book.authors ?? []

  return (
    <div className="card_country">
      <p className="card_country_id">id: {id}</p>
      <p className="card_country_name">nombre: {name}</p>
      <p className="card_country_title">título: {title}</p>
      <p className="card_country_isbn">ISBN: {isbn}</p>
      <p className="card_country_price">precio: {price}</p>
      <p className="card_country_authors">autores: {authors.map(a => a.name).join(', ')}</p>
    </div>
  )
}

export default BookCard