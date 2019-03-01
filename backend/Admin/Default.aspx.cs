using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;

public partial class _Default : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            string com = Session["cmd"].ToString();
            SqlDataAdapter da = new SqlDataAdapter(com,con);
            DataSet ds = new DataSet();
            da.Fill(ds);
            
            DataTable dt = new DataTable();
            dt.Columns.Add(new DataColumn("Latitude"));
            dt.Columns.Add(new DataColumn("Longitude"));

            DataRow row;
            

            int rows = ds.Tables[0].Rows.Count;
            for (int i = 0; i < rows; i++)
            {
                row = dt.NewRow();
                row["Latitude"] = ds.Tables[0].Rows[i][0].ToString();
                row["Longitude"] = ds.Tables[0].Rows[i][1].ToString();
                dt.Rows.Add(row);
            }
            js.Text = GPSLib.PlotGPSPoints(dt);
        }
    }
}