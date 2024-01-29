package models

type Role struct {
	ID       uint   `gorm:"primary_key"`
	RoleName string `gorm:"unique;not null"`
}
