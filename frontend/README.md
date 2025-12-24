# FastOp Frontend

This is a Vue 3 + Element Plus frontend for the FastOp system.

## Project Setup

1.  **Install Dependencies**:
    ```bash
    npm install
    ```

2.  **Run Development Server**:
    ```bash
    npm run dev
    ```
    This will start the server at `http://localhost:5173`.
    The `vite.config.js` is configured to proxy `/api` requests to `http://localhost:8080`. Ensure your backend is running on port 8080.

## Features

- **Function Designer**: Create and manage test functions, modules, cases, and steps.
- **Suite Designer**: Create test suites and bind functions to them.
- **Plan Execution**: Create test plans, dispatch, start, and pause execution.

## File Structure

- `src/api/index.js`: Axios configuration and API definitions.
- `src/App.vue`: Main application logic and UI.
- `src/main.js`: Application entry point.
