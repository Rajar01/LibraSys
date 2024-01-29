package main

import (
	"fmt"

	"github.com/gin-gonic/gin"
	"github.com/rajar01/librasys-backend/enums"
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
	db.AutoMigrate(
		&models.Role{},
		&models.User{},
		&models.Book{},
		&models.Loan{},
		&models.LoanDetail{},
	)

	initDatabase(db)

	userRepository := &repositories.UserRepository{DB: db}
	bookRepository := &repositories.BookRepository{DB: db}
	loanRepository := &repositories.LoanRepository{DB: db}

	authService := &services.AuthService{UserRepository: userRepository}
	userService := &services.UserService{UserRepository: userRepository}
	bookService := &services.BookService{BookRepository: bookRepository}
	loanService := &services.LoanService{LoanRepository: loanRepository}

	r := gin.Default()

	authHandler := &handlers.AuthHandler{AuthService: authService}
	userHandler := &handlers.UserHandler{UserService: userService}
	bookHandler := &handlers.BookHandler{BookService: bookService}
	loanHandler := &handlers.LoanHandler{LoanService: loanService}

	// Auth Route
	r.POST("/login", authHandler.Login)

	// User Route
	r.GET("/users", userHandler.GetUsers)
	r.GET("/users/:id", userHandler.GetUserById)
	r.POST("/users", userHandler.CreateUser)
	r.PUT("/users/:id", userHandler.UpdateUser)
	r.DELETE("/users/:id", userHandler.DeleteUserById)

	// Book Route
	r.GET("/books", bookHandler.GetBooks)
	r.GET("/books/:id", bookHandler.GetBookById)
	r.POST("/books", bookHandler.CreateBook)
	r.PUT("/books/:id", bookHandler.UpdateBook)
	r.DELETE("/books/:id", bookHandler.DeleteBookById)

	// Loan Route
	r.GET("/loans", loanHandler.GetLoans)
	r.GET("/loans/:id", loanHandler.GetLoanById)
	r.POST("/loans", loanHandler.CreateLoan)
	r.PUT("/loans/:id", loanHandler.UpdateLoan)
	r.DELETE("/loans/:id", loanHandler.DeleteLoanById)

	r.Run("localhost:8080")
}

func initDatabase(db *gorm.DB) {
	if isEmptyTable(db, &models.Status{}) {
		statuses := []models.Status{
			{StatusName: "Loan"},
			{StatusName: "Return"},
			{StatusName: "Delay"},
		}

		for _, status := range statuses {
			if err := db.Create(&status).Error; err != nil {
				panic(err)
			}
		}

		fmt.Println("Initialized status table.")
	} else {
		fmt.Println("Status table has been initialized. Skipping initialization.")
	}

	if isEmptyTable(db, &models.Role{}) {
		roles := []models.Role{
			{RoleName: enums.Admin.String()},
			{RoleName: enums.Member.String()},
		}

		for _, role := range roles {
			if err := db.Create(&role).Error; err != nil {
				panic(err)
			}
		}

		fmt.Println("Initialized role table.")
	} else {
		fmt.Println("Role table has been initialized. Skipping initialization.")
	}

	if isEmptyTable(db, &models.User{}) {
		adminRole := models.Role{}
		memberRole := models.Role{}

		if err := db.Where("role_name = ?", enums.Admin.String()).First(&adminRole).Error; err != nil {
			panic(err)
		}

		if err := db.Where("role_name = ?", enums.Member.String()).First(&memberRole).Error; err != nil {
			panic(err)
		}

		users := []models.User{
			{Username: "admin", Password: "123", RoleID: adminRole.ID},
			{Username: "member", Password: "123", Email: "member@gmail.com", RoleID: memberRole.ID},
		}

		for _, user := range users {
			if err := db.Create(&user).Error; err != nil {
				panic(err)
			}
		}

		fmt.Println("Initialized user table.")
	} else {
		fmt.Println("User table has been initialized. Skipping initialization.")
	}

	if isEmptyTable(db, &models.Book{}) {
		books := []models.Book{
			{ISBN: "978-3-16-148410-0", Title: "Book One", Author: "Author A", Publisher: "Publisher A"},
			{ISBN: "978-1-23-456789-7", Title: "Book Two", Author: "Author B", Publisher: "Publisher B"},
			{ISBN: "978-0-12-345678-9", Title: "Book Three", Author: "Author C", Publisher: "Publisher C"},
		}

		for _, book := range books {
			if err := db.Create(&book).Error; err != nil {
				panic(err)
			}
		}

		fmt.Println("Initialized book table.")
	} else {
		fmt.Println("Book table has been initialized. Skipping initialization.")
	}
}

func isEmptyTable(db *gorm.DB, model interface{}) bool {
	var count int64
	db.Model(model).Count(&count)
	return count == 0
}
