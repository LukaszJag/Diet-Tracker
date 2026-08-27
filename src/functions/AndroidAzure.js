const { app } = require("@azure/functions");
const sql = require("mssql");

app.http("AndroidAzure", {
  // Changed to POST so Android can send a large text body
  methods: ["POST"],
  authLevel: "anonymous",
  handler: async (request, context) => {
    try {
      // 1. Grab the raw SQL string from the Android POST request
      const body = await request.json();
      const rawSqlQuery = body.sql_query;

      if (!rawSqlQuery) {
        return {
          status: 400,
          body: "Error: No sql_query provided in request body.",
        };
      }

      context.log("Executing Android Query: " + rawSqlQuery);

      // 2. Standard Database Config
      const config = {
        user: "root-android-server",
        password: "~cTspR-67N$'A};",
        server: "first-server-android.database.windows.net",
        database: "free-sql-db-6758529",
        options: {
          encrypt: true,
          trustServerCertificate: true,
          enableArithAbort: true,
        },
      };

      // 3. Connect and Execute the custom Android Query
      let pool = await sql.connect(config);
      let result = await pool.request().query(rawSqlQuery);

      // 4. Send the data back as generic JSON (providing fallback if recordset is undefined)
      return {
        status: 200,
        jsonBody: result.recordset || [
          { success: true, rowsAffected: result.rowsAffected },
        ],
      };
    } catch (err) {
      context.error("SQL ERROR: " + err.message);
      return {
        status: 500,
        body: "Query Failed: " + err.message,
      };
    }
  },
});
