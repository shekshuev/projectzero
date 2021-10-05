const API_URL = () => {
    const API_VERSION = "1.0";
    if (process.env.NODE_ENV === "development")
        return `http://localhost:8080/api/v${API_VERSION}/`;
    else return `/api/v${API_VERSION}/`;
}

export {
    API_URL
}
