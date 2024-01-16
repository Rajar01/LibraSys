package repositories

import (
	"github.com/rajar01/librasys-backend/models"
	"gorm.io/gorm"
)

type UserRepository struct {
	DB *gorm.DB
}

func (userRepository *UserRepository) GetUserByUsername(username string) (*models.User, error) {
	var user models.User

	if err := userRepository.DB.Where(&models.User{Username: username}).First(&user).Error; err != nil {
		return nil, err
	}

	return &user, nil
}