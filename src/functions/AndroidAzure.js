const { app } = require("@azure/functions");
const sql = require("mssql");

app.http("AndroidAzure", {
  methods: ["GET", "POST"],
  authLevel: "anonymous",
  handler: async (request, context) => {
    context.log("Attempting to connect to SQL Database...");

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

      // Attempt to connect
      let pool = await sql.connect(config);

      // Execute the query (Replace 'Users' with your real SQL table name)
      let result = await pool
        .request()
        .query("SELECT TOP (1000) * FROM diet_tracker_schema.calendar");

      return {
        status: 200,
        jsonBody: result.recordset,
      };
    } catch (err) {
      // Log the error safely using V4 syntax
      context.error("DATABASE CRASH CAUGHT: " + err.message);

      // Send the raw error directly back to the browser screen!
      return {
        status: 200,
        body:
          "SQL CONNECTION FAILED!\n\nHere is the exact reason:\n" + err.message,
      };
    }
  },
});
