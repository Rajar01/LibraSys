package services

import (
	"github.com/rajar01/librasys-backend/enums"
	"github.com/rajar01/librasys-backend/models"
	"github.com/rajar01/librasys-backend/repositories"
)

type AuthService struct {
	UserRepository *repositories.UserRepository
}

func (s *AuthService) Login(username, password string) (*models.User, error) {
	user, err := s.UserRepository.GetUserByUsername(username)

	if err != nil {
		return nil, err
	}

	if user != nil && user.Password == password && user.Role.RoleName == enums.Admin.String() {
		return user, nil
	}

	return nil, nil
}
