package models

import "time"

type Loan struct {
	ID         uint      `gorm:"primary_key"`
	UserID     uint      `gorm:"not null"`
	LoanDate   time.Time `gorm:"not null"`
	LoanDetail []LoanDetail `gorm:"constraint:OnUpdate:CASCADE,OnDelete:CASCADE;"`
}
