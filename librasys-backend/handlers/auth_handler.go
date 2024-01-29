package handlers

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/rajar01/librasys-backend/services"
)

type AuthHandler struct {
	AuthService *services.AuthService
}

func (h *AuthHandler) Login(c *gin.Context) {
    var loginRequest struct {
        Username string `json:"username" binding:"required"`
        Password string `json:"password" binding:"required"`
    }

    if err := c.BindJSON(&loginRequest); err != nil {
        c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
        return
    }

    user, err := h.AuthService.Login(loginRequest.Username, loginRequest.Password)
    if err != nil {
        c.JSON(http.StatusInternalServerError, gin.H{"error": "Internal Server Error"})
        return
    }

    if user == nil {
        c.JSON(http.StatusUnauthorized, gin.H{"error": "Invalid credentials"})
        return
    }

    c.JSON(http.StatusOK, gin.H{"user": user})
}