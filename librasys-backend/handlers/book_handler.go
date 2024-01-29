package handlers

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/rajar01/librasys-backend/models"
	"github.com/rajar01/librasys-backend/services"
)

type BookHandler struct {
	BookService *services.BookService
}

func (h *BookHandler) GetBooks(c *gin.Context) {
	books, err := h.BookService.GetBooks()

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"books": books})
}

func (h *BookHandler) GetBookById(c *gin.Context) {
	bookIdParam := c.Param("id")
	bookId, err := strconv.ParseUint(bookIdParam, 10, 64)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid book ID"})
		return
	}

	book, err := h.BookService.GetBookById(uint(bookId))

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, book)
}

func (h *BookHandler) CreateBook(c *gin.Context) {
	var book models.Book

	if err := c.BindJSON(&book); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.BookService.CreateBook(&book); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusCreated, gin.H{"message": "book created successfully"})
}

func (h *BookHandler) UpdateBook(c *gin.Context) {
	bookIdParam := c.Param("id")
	bookId, err := strconv.ParseUint(bookIdParam, 10, 64)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid book ID"})
		return
	}

	var updatedBook models.Book
	if err := c.ShouldBindJSON(&updatedBook); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	updatedBook.ID = uint(bookId)

	if err := h.BookService.UpdateBook(&updatedBook); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "book updated successfully"})
}

func (h *BookHandler) DeleteBookById(c *gin.Context) {
	bookIdParam := c.Param("id")
	bookId, err := strconv.ParseUint(bookIdParam, 10, 64)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid book ID"})
		return
	}

	if err := h.BookService.DeleteBookById(uint(bookId)); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "book deleted successfully"})
}
