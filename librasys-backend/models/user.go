package models

type User struct {
	ID          uint   `gorm:"primary_key"`
	Username    string `gorm:"column:username;unique;not null"`
	Email       string `gorm:"column:email;unique"`
	Password    string `gorm:"column:password;not null" json:"-"`
	Name        string `gorm:"column:name"`
	Address     string `gorm:"column:address"`
	PhoneNumber string `gorm:"column:phone_number"`
	Role        int    `gorm:"column:role;not null"`
}
