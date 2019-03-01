using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Leave_Request : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);

    protected void Page_Load(object sender, EventArgs e)
    {
        fill();
    }

    public void fill()
    {
        string str = "Select L.Lid,D.Name As DName,D.Contact As Contact,L.Sdate,L.EDate,L.Reason,L.dt,L.Status from LeaveDetails L, DriverDetails D where D.Did = L.DId AND D.userid='" + Session["ID"].ToString() + "' ";
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
        if (e.CommandName == "Action")
        {
            string i1 = Convert.ToString(e.CommandArgument.ToString());

            lblLId.Text = i1;

            Panel3.Visible = true;
        }
    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        SqlCommand cmd = new SqlCommand("Update from LeaveDetails Set Status = '"+DropDownList1.Text+"' Where LId = '" + lblLId.Text + "'", con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();

        Page.ClientScript.RegisterStartupScript(GetType(), "msgbox", "alert('Leave Status Updated Successfully');", true);

        fill();
    }
}