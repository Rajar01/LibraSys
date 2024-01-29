package models

type Book struct {
	ID        uint   `gorm:"primary_key"`
	ISBN      string `gorm:"unique;not null"`
	Title     string `gorm:";unique;not null"`
	Author    string `gorm:"not null"`
	Publisher string `gorm:"not null"`
}
