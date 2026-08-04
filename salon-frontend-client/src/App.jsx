import { CssBaseline, ThemeProvider } from "@mui/material";
import "./App.css";
import { darkTheme } from "./theme/theme";
import HomePage from "./pages/HomePage";

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />

      <HomePage />
    </ThemeProvider>
  );
}

export default App;
