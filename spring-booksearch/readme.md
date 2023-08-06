# Hibernate search test project
# Table of content:
- [Testing dev build](#testing-dev-build)
  - [Uploading books](#uploading-books)
  - [Using search](#using-search)

# Testing dev build
## Uploading books
To check that app is working, first upload some books by running AppRunner with console parameters:

`--load-book="/path/to/book"`
and
`--rebuild-index`

(order of which does not matter).
This will upload specified epub book to spring repository and create/rebuild Lucene index.

If you want application to automatically close after that, also pass the

`--autostop` parameter.

## Using search
To start a server run BooksearchApplication class or don't pass 

`--autostop` parameter while uploading new books

and navigate to `localhost:8080` in your browser.
There you will be presented with search bar, accepting arbitrary phrase
to be search in indexed content. If you certain that phrase, you are looking for is
not found by the program, make sure you've rebuilt Lucene index as told in [previous section](#uploading-books).

Note that current program version is using "strict" search algorithm instead of fuzzy-search just as an example.