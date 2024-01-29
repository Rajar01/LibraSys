package models

type User struct {
	ID          uint   `gorm:"primary_key"`
	Username    string `gorm:"unique;not null"`
	Email       string `gorm:"unique"`
	Password    string `gorm:"not null"`
	Name        string `gorm:""`
	Address     string `gorm:""`
	PhoneNumber string `gorm:""`
	RoleID      uint   `gorm:""`
	Role        Role
	Loan        []Loan `gorm:"constraint:OnUpdate:CASCADE,OnDelete:CASCADE;"`
}
