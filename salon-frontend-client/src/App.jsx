import { CssBaseline, ThemeProvider } from "@mui/material";
import "./App.css";
import { darkTheme } from "./theme/theme";
import BranchDetailsPage from "./pages/BranchDetailsPage";

//import HomePage from "./pages/HomePage";

function App() {
  return (
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />

      {/* <HomePage /> */}
     <BranchDetailsPage />
    </ThemeProvider>
  );
}

export default App;
