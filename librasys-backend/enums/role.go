package enums

type Role int

const (
	Admin Role = iota
	Member
)

func (r Role) String() string {
	return [...]string{"Admin", "Member"}[r]
}
