import { useNavigate, Link } from "react-router-dom";
import { useAuthStore } from "@/store/authStore";

function Navbar() {
  const navigate = useNavigate();
  const logout = useAuthStore((state) => state.logout);

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div>
        <h1>
          <Link to="/">Language Journal</Link>
        </h1>
        <Link to="/">Dashboard</Link>
        <Link to="/languages">Languages</Link>
      </div>
      <div>
        <button onClick={handleLogout}>Logout</button>
      </div>
    </nav>
  );
}

export default Navbar;
