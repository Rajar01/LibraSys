package repositories

import (
	"github.com/rajar01/librasys-backend/models"
	"gorm.io/gorm"
)

type LoanRepository struct {
	DB *gorm.DB
}

func (loanRepository *LoanRepository) GetLoans() ([]models.Loan, error) {
	var loans []models.Loan

	if err := loanRepository.DB.
		Preload("LoanDetail").
		Preload("LoanDetail.Book").
		Preload("LoanDetail.Status").
		Find(&loans).Error; err != nil {
		return nil, err
	}

	return loans, nil
}

func (loanRepository *LoanRepository) GetLoanById(loanID uint) (*models.Loan, error) {
	var loan models.Loan

	if err := loanRepository.DB.
		Preload("LoanDetail").
		Preload("LoanDetail.Book").
		Preload("LoanDetail.Status").
		Where(&models.Loan{ID: loanID}).First(&loan).Error; err != nil {
		return nil, err
	}

	return &loan, nil
}

func (loanRepository *LoanRepository) CreateLoan(loan *models.Loan) error {
	if err := loanRepository.DB.Create(loan).Error; err != nil {
		return err
	}

	return nil
}

func (loanRepository *LoanRepository) UpdateLoan(loan *models.Loan) error {
	if err := loanRepository.DB.Save(loan).Error; err != nil {
		return err
	}

	for _, detail := range loan.LoanDetail {
		if err := loanRepository.DB.Save(&detail).Error; err != nil {
			return err
		}
	}

	return nil
}

func (loanRepository *LoanRepository) DeleteLoanById(loanID uint) error {
	if err := loanRepository.DB.Select("LoanDetail").Delete(&models.Loan{ID: loanID}).Error; err != nil {
		return err
	}

	return nil
}
