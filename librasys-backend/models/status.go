package models

type Status struct {
	ID         uint   `gorm:"primary_key"`
	StatusName string `gorm:"unique;not null"`
}
