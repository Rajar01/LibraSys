package services

import (
	"github.com/rajar01/librasys-backend/models"
	"github.com/rajar01/librasys-backend/repositories"
)

type UserService struct {
	UserRepository *repositories.UserRepository
}

func (s *UserService) GetUsers() ([]models.User, error) {
	return s.UserRepository.GetUsers()
}

func (s *UserService) GetUserById(userID uint) (*models.User, error) {
	return s.UserRepository.GetUserById(userID)
}

func (s *UserService) CreateUser(user *models.User) error {
	return s.UserRepository.CreateUser(user)
}

func (s *UserService) UpdateUser(user *models.User) error {
	return s.UserRepository.UpdateUser(user)
}

func (s *UserService) DeleteUserById(userID uint) error {
	return s.UserRepository.DeleteUserById(userID)
}
