using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class SpeedAlert : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);

    protected void Page_Load(object sender, EventArgs e)
    {
        fill();
    }

    public void fill()
    {
        string str = "Select D.Did,D.Name,D.Contact,S.latlng,S.SDate,S.STime from DriverDetails D, SpeedDetails S where S.Did = D.ID AND D.userid='" + Session["ID"].ToString() + "' ";
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
        if (e.CommandName == "Delete")
        {
            string i1 = Convert.ToString(e.CommandArgument.ToString());
            string[] data = i1.Split('*');
            SqlCommand cmd = new SqlCommand("Delete from SpeedDetails Where DId = '" + data[0] + "' AND SDate = '"+data[1]+"', STime = '"+data[2]+"'", con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();

            Page.ClientScript.RegisterStartupScript(GetType(), "msgbox", "alert('Speed Limit Alert Deleted Successfully');", true);
            
            fill();
        }
    }
}