import './App.css'
import axios from "axios";
import {useState} from "react";
import Card from "./Card.tsx";

interface Author {
  id: number;
  name: string;
}

interface Book {
    isbn: string;
    price: number;
    title: string;
    authors: Array<Author>;
}

function App() {

  const [authors, setAuthors] = useState<Author[]>([]);
  const [books, setBooks] = useState<Book[]>([]);

  const handleClicklistarAuthores = (): void => {
    axios.get<Author[]>("http://localhost/app-authors/authors")
        .then(response => {
          setAuthors(response.data);
        })
        .catch(error => alert(error));
  };

  const handleClickListarBooks = (): void => {
    axios.get<Book[]>("http://localhost/app-books/books")
        .then(response => {
          setBooks(response.data);
        })
        .catch(error => alert(error));
  };


  return (
    <>
      <section id="center">
        <div>
            <h1>Autores</h1>

        </div>

        <button onClick={handleClicklistarAuthores}> Consultar </button>

          <br />

         <div className="container">
             {
                 authors.map((item, index) => (
                     <Card
                     key={ index }
                     autor={item}
                     />
                 ))
             }

         </div>
          {/*{*/}
          {/*    authors.map(author =>*/}
          {/*        <p key={author.id}>{author.id} - {author.name}</p>*/}

          {/*    )*/}
          {/*}*/}



      </section>

        <section id="center">
            <div>
                <h1>Books</h1>

            </div>

            <button onClick={handleClickListarBooks}> Consultar </button>

            <br />
            {/*<div className="container">*/}
            {/*    {*/}
            {/*        books.map((item, index) => (*/}
            {/*            <CardBook*/}
            {/*            key = {index}*/}
            {/*            book={item}*/}
            {/*            >*/}
            {/*        ))*/}
            {/*    }*/}

            {/*</div>*/}

            {
                books.map(book => (
                    <ul key={book.isbn}>
                        <li>{book.isbn} - {book.price} - {book.title}</li>
                        {
                            book.authors.map(author => (
                                <li key={author.id}>{author.name}</li>
                            ))
                        }
                    </ul>
                ))
            }

        </section>


        <br/>

    </>
  )
}

export default App
