package main

import (
	"github.com/gin-gonic/gin"
	"github.com/rajar01/librasys-backend/handlers"
	"github.com/rajar01/librasys-backend/models"
	"github.com/rajar01/librasys-backend/repositories"
	"github.com/rajar01/librasys-backend/services"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

func main() {
	// Set up the database connection
	dsn := "root:@tcp(localhost:3306)/librasys?charset=utf8mb4&parseTime=True&loc=Local"
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})

	if err != nil {
		panic("Failed to connect to database")
	}

	// AutoMigrate will attempt to automatically migrate your schema, including
	// creating tables for your models
	db.AutoMigrate(&models.User{})
	
	userRepository := &repositories.UserRepository{DB: db}
    authService := &services.AuthService{UserRepository: userRepository}

	r := gin.Default()

	authHandler := &handlers.AuthHandler{AuthService: authService}
	
    r.POST("/login", authHandler.Login)

	r.Run("localhost:8080")
}
