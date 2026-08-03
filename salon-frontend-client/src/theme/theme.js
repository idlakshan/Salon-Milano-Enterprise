import { createTheme } from "@mui/material/styles";
import { milanoColors } from "./colors";

export const darkTheme = createTheme({
  palette: {
    mode: "dark",
    primary: {
      main: milanoColors.red.main,
      dark: milanoColors.red.hover,
      light: milanoColors.red.light,
      contrastText: "#FFFFFF",
    },
    secondary: {
      main: milanoColors.silver.main,
      dark: milanoColors.silver.dark,
      light: milanoColors.silver.light,
    },
    background: {
      default: milanoColors.dark.bg,
      paper: milanoColors.dark.paper,
    },
    text: {
      primary: "#FFFFFF",
      secondary: milanoColors.silver.dark,
    },
    divider: milanoColors.dark.border,
  },
  typography: {
    fontFamily: ['"Inter"', '"Plus Jakarta Sans"', "sans-serif"].join(","),
    h1: { fontWeight: 700 },
    h2: { fontWeight: 700 },
    h3: { fontWeight: 600 },
    button: { textTransform: "none", fontWeight: 600 },
  },
  shape: {
    borderRadius: 10,
  },
});

// Light Theme
export const lightTheme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: milanoColors.red.main,
      dark: milanoColors.red.hover,
      light: milanoColors.red.light,
      contrastText: "#FFFFFF",
    },
    secondary: {
      main: milanoColors.silver.dark,
    },
    background: {
      default: milanoColors.light.bg,
      paper: milanoColors.light.paper,
    },
    text: {
      primary: milanoColors.light.textPrimary,
      secondary: milanoColors.light.textSecondary,
    },
    divider: milanoColors.light.border,
  },
  typography: {
    fontFamily: ['"Inter"', '"Plus Jakarta Sans"', "sans-serif"].join(","),
    button: { textTransform: "none", fontWeight: 600 },
  },
  shape: {
    borderRadius: 10,
  },
});
