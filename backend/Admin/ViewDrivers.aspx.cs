using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
public partial class ViewDrivers : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);
    protected void Page_Load(object sender, EventArgs e)
    {
        fill();
    }

    public void fill()
    {
        string str = "Select id,name,contact,address,age,vehicleno,salary,userid from driverdetails where userid='" + Session["ID"].ToString() + "' ";
        SqlDataAdapter da = new SqlDataAdapter(str, con);
        DataSet ds = new DataSet();
        da.Fill(ds);
        if (ds.Tables[0].Rows.Count > 0)
        {
            GridView1.DataSource = ds;
            GridView1.DataBind();
        }
        else
        {
            GridView1.Visible = false;
            lblMsg.Text = "Currently, No Records found...!!";
            lblMsg.Visible = true;
        }
    }

    protected void GridView1_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "Change")
        {
            lblID.Text = Convert.ToString(e.CommandArgument.ToString());

            Panel3.Visible = true;
        }
    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        SqlCommand cmd = new SqlCommand("Update DriverDetails Set Speed = '" + TextBox1.Text + "' Where Id = '" + lblID.Text + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();

        Page.ClientScript.RegisterStartupScript(GetType(), "msgbox", "alert('Speed Limit Updated Successfully');", true);

        Panel3.Visible = false;

        fill();
    }
}