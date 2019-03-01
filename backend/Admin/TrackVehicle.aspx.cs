using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

public partial class TrackVehicle : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            string str = "Select name from driverdetails where userid='" + Session["ID"].ToString() + "'";
            SqlDataAdapter da = new SqlDataAdapter(str, con);
            DataSet ds = new DataSet();
            da.Fill(ds);
            if (ds.Tables[0].Rows.Count > 0)
            {
                DropDownList1.Items.Add("--Select Name--");
                for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
                {
                    string name = ds.Tables[0].Rows[i][0].ToString();
                    DropDownList1.Items.Add(name);
                }
            }
        }
    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        string dy = System.DateTime.Now.ToString("yyyy");
        string dm = System.DateTime.Now.ToString("MM");
        string dd = System.DateTime.Now.ToString("dd");

        string date = dy + "-" + dm + "-" + dd;


        SqlCommand cmd = new SqlCommand("Select Lat,Long from trackdetails where driverId='" + LabelDid.Text + "' AND Date='" + date + "';", con);
        con.Open();
        SqlDataReader dr = cmd.ExecuteReader();
        if (dr.HasRows)
        {
            con.Close();
            Session["cmd"] = "Select Lat,Long from trackdetails where driverId='" + LabelDid.Text + "' AND Date='" + date + "' order by Time";
            Response.Redirect("Default.aspx");
        }
        else
        {
            lblMsg.Text = "Currently, No Data found...!!";
            lblMsg.Visible = true;
        }
    }

    protected void DropDownList1_SelectedIndexChanged(object sender, EventArgs e)
    {
        lblMsg.Visible = false;
        if (DropDownList1.Text == "--Select Name--")
        {
            DropDownList1.BorderColor = System.Drawing.Color.Red;
            LabelErr.Visible = true;
            LabelErr.ForeColor = System.Drawing.Color.Red;
            Button1.Visible = false;
        }
        else if (DropDownList1.Text != "--Select Name--")
        {
            DropDownList1.BorderColor = System.Drawing.Color.Empty;
            LabelErr.Visible = false;
            Button1.Visible = true;
            string sel = "select id,vehicleno from driverdetails where name='" + DropDownList1.SelectedItem.Text + "'";
            SqlDataAdapter da = new SqlDataAdapter(sel, con);
            DataSet ds = new DataSet();
            da.Fill(ds);
            if (ds.Tables[0].Rows.Count > 0)
            {
                LabelDid.Text = ds.Tables[0].Rows[0][0].ToString();
                LabelVehNo.Text = ds.Tables[0].Rows[0][1].ToString();
            }
        }
    }
}