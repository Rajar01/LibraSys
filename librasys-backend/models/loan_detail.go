package models

import "time"

type LoanDetail struct {
	ID         uint      `gorm:"primary_key"`
	LoanID     uint      `gorm:""`
	BookID     uint      `gorm:""`
	ReturnDate time.Time `gorm:"not null"`
	Fine       uint64    `gorm:""`
	StatusID   uint      `gorm:""`
	Book       Book      `gorm:"constraint:OnUpdate:CASCADE,OnDelete:CASCADE;"`
	Status     Status    `gorm:"constraint:OnUpdate:CASCADE,OnDelete:CASCADE;"`
}
