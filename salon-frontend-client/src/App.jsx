
import { Button, CssBaseline, ThemeProvider } from "@mui/material";
import "./App.css";
import { darkTheme } from "./theme/theme";

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
  
  <Button variant="contained">Button</Button>
  
    </ThemeProvider>
  );
}

export default App;
