package services

import (
	"github.com/rajar01/librasys-backend/models"
	"github.com/rajar01/librasys-backend/repositories"
)

type LoanService struct {
	LoanRepository *repositories.LoanRepository
}

func (s *LoanService) GetLoans() ([]models.Loan, error) {
	return s.LoanRepository.GetLoans()
}

func (s *LoanService) GetLoanById(loanID uint) (*models.Loan, error) {
	return s.LoanRepository.GetLoanById(loanID)
}

func (s *LoanService) CreateLoan(loan *models.Loan) error {
	return s.LoanRepository.CreateLoan(loan)
}

func (s *LoanService) UpdateLoan(loan *models.Loan) error {
	return s.LoanRepository.UpdateLoan(loan)
}

func (s *LoanService) DeleteLoanById(loanID uint) error {
	return s.LoanRepository.DeleteLoanById(loanID)
}
