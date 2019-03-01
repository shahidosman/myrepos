using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

public partial class UserRegistration : System.Web.UI.Page
{
    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);
    protected void Page_Load(object sender, EventArgs e)
    {
        if(Session["Add"]=="Data")
        {
            Page.ClientScript.RegisterStartupScript(GetType(), "msgtype", "alert('Success. Registration Done')", true);
            Session["Add"] = "";
        }
        if (!IsPostBack)
        {
            string str = "Select top 1 id from userdetails order by id desc";
            SqlDataAdapter da = new SqlDataAdapter(str, con);
            DataSet ds = new DataSet();
            da.Fill(ds);
            if (ds.Tables[0].Rows.Count == 0)
            {
                LabelId.Text = "1001";
            }
            else
            {
                string s = ds.Tables[0].Rows[0][0].ToString();
                int s1 = Convert.ToInt32(s) + 1;
                LabelId.Text = s1.ToString();
            }
        }
    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        string str = "select email from userdetails where email='" + TextBox3.Text + "'";
        SqlDataAdapter da = new SqlDataAdapter(str, con);
        DataSet ds = new DataSet();
        da.Fill(ds);
        if (ds.Tables[0].Rows.Count > 0)
        {
            TextBox3.BorderColor = System.Drawing.Color.Red;
            LabelErr.Visible = true;
        }
        else
        {
            string ins = "Insert into userdetails values('" + LabelId.Text + "','" + TextBox1.Text + "','" + TextBox2.Text + "','" + TextBox3.Text + "','" + TextBox4.Text + "','"+TextBox5.Text+"','" + Password1.Value + "')";
            SqlCommand cmd = new SqlCommand(ins, con);
            con.Open();
            cmd.ExecuteNonQuery();
            con.Close();
            Session["Add"] = "Data";
            Response.Redirect("UserRegistration.aspx");
        }
    }
}