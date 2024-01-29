package services

import (
	"github.com/rajar01/librasys-backend/models"
	"github.com/rajar01/librasys-backend/repositories"
)

type BookService struct {
	BookRepository *repositories.BookRepository
}

func (s *BookService) GetBooks() ([]models.Book, error) {
	return s.BookRepository.GetBooks()
}

func (s *BookService) GetBookById(bookID uint) (*models.Book, error) {
	return s.BookRepository.GetBookById(bookID)
}

func (s *BookService) CreateBook(book *models.Book) error {
	return s.BookRepository.CreateBook(book)
}

func (s *BookService) UpdateBook(book *models.Book) error {
	return s.BookRepository.UpdateBook(book)
}

func (s *BookService) DeleteBookById(bookID uint) error {
	return s.BookRepository.DeleteBookById(bookID)
}
