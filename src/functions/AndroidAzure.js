const { app } = require("@azure/functions");
const sql = require("mssql");

app.http("AndroidAzure", {
  methods: ["GET", "POST"],
  authLevel: "anonymous",
  handler: async (request, context) => {
    context.log("Attempting to connect to SQL Database...");
    const requestedTable = request.query.get("table") || "calendar";

    let sqlQuery = "";

    // 2. IMPORTANT: We use a Switch statement to prevent SQL Injection hackers!
    switch (requestedTable) {
      case "calendar":
        sqlQuery = "SELECT * FROM diet_tracker_schema.calendar";
        break;
      case "products":
        sqlQuery = "SELECT * FROM diet_tracker_schema.products"; // Example of a second table
        break;
      case "days_statistics_test":
        sqlQuery = "SELECT * FROM diet_tracker_schema.days_statistics_test"; // Example of a third table
        break;
      default:
        return { status: 400, body: "Invalid table requested!" };
    }

    try {
      // Check carefully! Ensure no accidental spaces inside the quotes!
      const config = {
        user: "root-android-server", // Type your SQL login username here
        password: "~cTspR-67N$'A};", // Type your SQL password here
        server: "first-server-android.database.windows.net", // I pulled this from your screenshot!
        database: "free-sql-db-6758529", // I pulled this
        options: {
          encrypt: true,
          trustServerCertificate: true, // This stops the instant SSL certificate crash
          enableArithAbort: true,
        },
      };

      let pool = await sql.connect(config);
      let result = await pool.request().query(sqlQuery);

      return { status: 200, jsonBody: result.recordset };
    } catch (err) {
      return { status: 500, body: "Error: " + err.message };
    }
  },
});
