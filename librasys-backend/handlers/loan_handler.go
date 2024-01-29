package handlers

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"github.com/rajar01/librasys-backend/models"
	"github.com/rajar01/librasys-backend/services"
)

type LoanHandler struct {
	LoanService *services.LoanService
}

func (h *LoanHandler) GetLoans(c *gin.Context) {
	loans, err := h.LoanService.GetLoans()

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"loans": loans})
}

func (h *LoanHandler) GetLoanById(c *gin.Context) {
	loanIdParam := c.Param("id")
	loanId, err := strconv.ParseUint(loanIdParam, 10, 64)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid loan ID"})
		return
	}

	loan, err := h.LoanService.GetLoanById(uint(loanId))

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"loan": loan})
}

func (h *LoanHandler) CreateLoan(c *gin.Context) {
	var loan models.Loan

	if err := c.BindJSON(&loan); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := h.LoanService.CreateLoan(&loan); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusCreated, gin.H{"message": "loan created successfully"})
}

func (h *LoanHandler) UpdateLoan(c *gin.Context) {
	loanIdParam := c.Param("id")
	loanId, err := strconv.ParseUint(loanIdParam, 10, 64)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid loan ID"})
		return
	}

	var updatedLoan models.Loan
	if err := c.ShouldBindJSON(&updatedLoan); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	updatedLoan.ID = uint(loanId)

	if err := h.LoanService.UpdateLoan(&updatedLoan); err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "loan updated successfully"})
}

func (h *LoanHandler) DeleteLoanById(c *gin.Context) {
	loanIdParam := c.Param("id")
	loanId, err := strconv.ParseUint(loanIdParam, 10, 64)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid loan ID"})
		return
	}

	if err := h.LoanService.DeleteLoanById(uint(loanId)); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "loan deleted successfully"})
}
