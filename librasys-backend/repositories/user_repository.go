package repositories

import (
	"github.com/rajar01/librasys-backend/models"
	"gorm.io/gorm"
)

type UserRepository struct {
	DB *gorm.DB
}

func (userRepository *UserRepository) GetUsers() ([]models.User, error) {
	var users []models.User

	if err := userRepository.DB.
		Preload("Role").
		Preload("Loan").
		Preload("Loan.LoanDetail").
		Preload("Loan.LoanDetail.Book").
		Preload("Loan.LoanDetail.Status").
		Find(&users).Error; err != nil {
		return nil, err
	}

	return users, nil
}

func (userRepository *UserRepository) GetUserByUsername(username string) (*models.User, error) {
	var user models.User

	if err := userRepository.DB.
		Preload("Role").
		Preload("Loan").
		Preload("Loan.LoanDetail").
		Preload("Loan.LoanDetail.Book").
		Preload("Loan.LoanDetail.Status").
		Where(&models.User{Username: username}).First(&user).Error; err != nil {
		return nil, err
	}

	return &user, nil
}

func (userRepository *UserRepository) GetUserById(userID uint) (*models.User, error) {
	var user models.User

	if err := userRepository.DB.
		Preload("Role").
		Preload("Loan").
		Preload("Loan.LoanDetail").
		Preload("Loan.LoanDetail.Book").
		Preload("Loan.LoanDetail.Status").
		Where(&models.User{ID: userID}).First(&user).Error; err != nil {
		return nil, err
	}

	return &user, nil
}

func (userRepository *UserRepository) CreateUser(user *models.User) error {
	if err := userRepository.DB.
		Create(user).Error; err != nil {
		return err
	}

	return nil
}

func (userRepository *UserRepository) UpdateUser(user *models.User) error {
	if err := userRepository.DB.Save(user).Error; err != nil {
		return err
	}

	return nil
}

func (userRepository *UserRepository) DeleteUserById(userID uint) error {
	if err := userRepository.DB.Delete(&models.User{}, userID).Error; err != nil {
		return err
	}

	return nil
}
