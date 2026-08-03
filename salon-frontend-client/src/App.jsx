import { CssBaseline, ThemeProvider } from "@mui/material";
import "./App.css";
import { darkTheme } from "./theme/theme";
import Home from "./pages/Home";

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />

      <Home />
    </ThemeProvider>
  );
}

export default App;
