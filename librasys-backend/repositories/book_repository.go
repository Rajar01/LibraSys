package repositories

import (
	"github.com/rajar01/librasys-backend/models"
	"gorm.io/gorm"
)

type BookRepository struct {
	DB *gorm.DB
}

func (bookRepository *BookRepository) GetBooks() ([]models.Book, error) {
	var books []models.Book

	if err := bookRepository.DB.Find(&books).Error; err != nil {
		return nil, err
	}

	return books, nil
}

func (bookRepository *BookRepository) GetBookById(bookID uint) (*models.Book, error) {
	var book models.Book

	if err := bookRepository.DB.Where(&models.Book{ID: bookID}).First(&book).Error; err != nil {
		return nil, err
	}

	return &book, nil
}

func (bookRepository *BookRepository) CreateBook(book *models.Book) error {
	if err := bookRepository.DB.Create(book).Error; err != nil {
		return err
	}

	return nil
}

func (bookRepository *BookRepository) UpdateBook(book *models.Book) error {
	if err := bookRepository.DB.Save(book).Error; err != nil {
		return err
	}

	return nil
}

func (bookRepository *BookRepository) DeleteBookById(bookID uint) error {
	if err := bookRepository.DB.Delete(&models.Book{}, bookID).Error; err != nil {
		return err
	}

	return nil
}
