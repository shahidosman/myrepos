using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

public partial class Login : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        string str = "select id,email,password from userdetails where email='" + TextBox1.Text + "'";
        SqlDataAdapter da1 = new SqlDataAdapter(str, con);
        DataSet ds1 = new DataSet();
        da1.Fill(ds1);
        if (ds1.Tables[0].Rows.Count > 0)
        {
            Session["ID"] = ds1.Tables[0].Rows[0][0].ToString();
            Session["Email"] = ds1.Tables[0].Rows[0][1].ToString();
            string pass = ds1.Tables[0].Rows[0][2].ToString();
            if (pass==TextBox2.Text)
            Session["Type"] = "User";
            Response.Redirect("AddDriver.aspx");
        }
        else
        {
            Page.ClientScript.RegisterStartupScript(GetType(), "msgtype", "alert('Login Failed. Incorrect Email ID or Password')", true);
        }
    }
}