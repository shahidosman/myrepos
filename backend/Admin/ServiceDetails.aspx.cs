using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

public partial class ServiceDetails : System.Web.UI.Page
{

    SqlConnection con = new SqlConnection(ConfigurationManager.ConnectionStrings["VehicleTrack"].ConnectionString);
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            string id = Session["ID"].ToString();
            string str = "select vehicleno from driverdetails where userid='" + id + "'";
            SqlDataAdapter da1 = new SqlDataAdapter(str, con);
            DataSet ds1 = new DataSet();
            da1.Fill(ds1);
            if (ds1.Tables[0].Rows.Count > 0)
            {
                DropDownList1.Items.Add("--Select Vehicle--");
                {
                    for (int i = 0; i < ds1.Tables[0].Rows.Count; i++)
                    {
                        string val = ds1.Tables[0].Rows[i][0].ToString();
                        DropDownList1.Items.Add(val);
                    }
                }
            }
            else
            {
                DropDownList1.Items.Add("--No Data--");
            }
            Panel1.Visible = false;
            Panel2.Visible = false;
        }
    }
    protected void DropDownList1_SelectedIndexChanged(object sender, EventArgs e)
    {
        SqlCommand cmd;
        string del = "delete from tservice";
        cmd = new SqlCommand(del, con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        string dy = System.DateTime.Now.ToString("yyyy");
        string dm = System.DateTime.Now.ToString("MM");
        string dd = System.DateTime.Now.ToString("dd");
        int dd1 = Convert.ToInt32(dd);
        dd1 = Convert.ToInt32("0");
        int year = Convert.ToInt32(dy);
        int month = Convert.ToInt32(dm);
        int end = System.DateTime.DaysInMonth(year, month);
        for (int j = 1; j <= end; j++)
        {
            int dd2 = dd1 + j;
            string start = dy + "-" + dm + "-" + dd2.ToString("00");
            Label8.Text = start;
            string sel = "select amt,date,vehicleno from servicedetails where vehicleno='" + DropDownList1.SelectedItem.Text + "' AND Date = '" + start + "'";
            SqlDataAdapter da1 = new SqlDataAdapter(sel, con);
            DataSet ds1 = new DataSet();
            da1.Fill(ds1);
            if (ds1.Tables[0].Rows.Count > 0)
            {
                string veh = ds1.Tables[0].Rows[0][2].ToString();
                string dr = "select name from driverdetails where vehicleno='" + veh + "'";
                SqlDataAdapter dat = new SqlDataAdapter(dr, con);
                DataSet dst = new DataSet();
                dat.Fill(dst);
                if (dst.Tables[0].Rows.Count > 0)
                {
                    string dri = dst.Tables[0].Rows[0][0].ToString();
                    string ins1 = "insert into tempservice values('" + dri + "','" + ds1.Tables[0].Rows[0][2].ToString() + "','" + ds1.Tables[0].Rows[0][0].ToString() + "','" + ds1.Tables[0].Rows[0][1].ToString() + "')";
                    cmd = new SqlCommand(ins1, con);
                    con.Open();
                    cmd.ExecuteNonQuery();
                    con.Close();
                }
                string ins = "insert into tservice values('" + start + "','" + ds1.Tables[0].Rows[0][0].ToString() + "')";
                cmd = new SqlCommand(ins, con);
                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();
            }
            else
            {
                string ins = "insert into tservice values('" + start + "','0')";
                cmd = new SqlCommand(ins, con);
                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();
            }
        }
        SqlDataAdapter da;
        DataSet ds = new DataSet();
        string ol = "select drivername,VehicleNo,sum(Amt) as Amt,date from tempservice where vehicleno='" + DropDownList1.SelectedItem.Text + "' group by drivername,VehicleNo,Amt,date";
        da = new SqlDataAdapter(ol, con);
        da.Fill(ds);
        if (ds.Tables[0].Rows.Count > 0)
        {
            DropDownList2.Items.Clear();
            DropDownList2.Items.Add("--Select--");
            DropDownList2.Items.Add("Date");
            Panel1.Visible = true;
            Panel2.Visible = false;
            DropDownList1.BorderColor = System.Drawing.Color.Empty;
            Panel3.Visible = true;
            GridView1.DataSource = ds;
            GridView1.DataBind();
            int i = ds.Tables[0].Rows.Count;
            int tot = 0;
            while (i > 0)
            {

                tot += (Convert.ToInt32(ds.Tables[0].Rows[i - 1][2].ToString()));
                i--;
            }
            LabelTotal.Text = tot.ToString();
            Label4.Visible = true;
            LabelTotal.Visible = true;
            string sel1 = "select dates,amount from tservice";
            da = new SqlDataAdapter(sel1, con);
            da.Fill(ds);
            Chart1.DataSource = ds;
            Chart1.Series["Series1"].XValueMember = "dates";
            Chart1.Series["Series1"].YValueMembers = "amount";
            Chart1.DataBind();
        }
        else if (DropDownList1.Text == "--Select Vehicle--")
        {
            DropDownList2.Items.Clear();
            DropDownList2.Items.Add("--Select--");
            DropDownList2.Items.Add("Date");
            DropDownList1.BorderColor = System.Drawing.Color.Red;
            DropDownList2.BorderColor = System.Drawing.Color.Empty;
            Panel3.Visible = false;
            Label4.Visible = false;
            LabelTotal.Text = "";
            LabelTotal.Visible = false;
            Panel1.Visible = false;
            Panel2.Visible = false;
        }
        else
        {
            DropDownList2.Items.Clear();
            DropDownList2.Items.Add("--Select--");
            DropDownList2.Items.Add("Date");
            Panel1.Visible = false;
            Panel2.Visible = false;
            Panel3.Visible = false;
            Label4.Visible = false;
            LabelTotal.Text = "";
            LabelTotal.Visible = false;
            DropDownList1.BorderColor = System.Drawing.Color.Empty;
            Page.ClientScript.RegisterStartupScript(GetType(), "msgtype()", "alert('No Data Available !!!')", true);
        }
    }

    protected void DropDownList2_SelectedIndexChanged(object sender, EventArgs e)
    {
        if (DropDownList2.Text == "Date")
        {
            Panel1.Visible = true;
            Panel2.Visible = true;
            Panel3.Visible = false;
            DropDownList2.BorderColor = System.Drawing.Color.Empty;
        }
        else if (DropDownList2.Text == "--Select--")
        {
            Panel1.Visible = true;
            Panel2.Visible = false;
            Panel3.Visible = false;
            DropDownList2.BorderColor = System.Drawing.Color.Red;
        }
    }


    protected void Button1_Click1(object sender, EventArgs e)
    {
        string from = Calendar1.SelectedDate.ToString("yyyy-MM-dd");
        string to = Calendar2.SelectedDate.ToString("yyyy-MM-dd");
        SqlCommand cmd;
        string del = "delete from tservice";
        cmd = new SqlCommand(del, con);
        con.Open();
        cmd.ExecuteNonQuery();
        con.Close();
        string dy = System.DateTime.Now.ToString("yyyy");
        string dm = System.DateTime.Now.ToString("MM");
        string dd = System.DateTime.Now.ToString("dd");
        int dd1 = Convert.ToInt32(dd);
        dd1 = Convert.ToInt32("0");
        int year = Convert.ToInt32(dy);
        int month = Convert.ToInt32(dm);
        int end = System.DateTime.DaysInMonth(year, month);
        for (int j = 1; j <= end; j++)
        {
            int dd2 = dd1 + j;
            string start = dy + "-" + dm + "-" + dd2.ToString("00");
            Label8.Text = start;
            string sel = "select amt,date,vehicleno from servicedetails where vehicleno='" + DropDownList1.SelectedItem.Text + "' AND Date = '" + start + "'";
            SqlDataAdapter da1 = new SqlDataAdapter(sel, con);
            DataSet ds1 = new DataSet();
            da1.Fill(ds1);
            if (ds1.Tables[0].Rows.Count > 0)
            {
                string ins = "insert into tservice values('" + start + "','" + ds1.Tables[0].Rows[0][0].ToString() + "')";
                cmd = new SqlCommand(ins, con);
                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();
            }
            else
            {
                string ins = "insert into tservice values('" + start + "','0')";
                cmd = new SqlCommand(ins, con);
                con.Open();
                cmd.ExecuteNonQuery();
                con.Close();
            }
        }
        SqlDataAdapter da;
        DataSet ds = new DataSet();
        string ol = "select drivername,VehicleNo,sum(Amt) as Amt,date from tempservice where date>='" + from + "' and date<='" + to + "' and vehicleno='" + DropDownList1.SelectedItem.Text + "' group by drivername,VehicleNo,Amt,date";
        da = new SqlDataAdapter(ol, con);
        da.Fill(ds);
        if (ds.Tables[0].Rows.Count > 0)
        {
            Panel1.Visible = true;
            DropDownList1.BorderColor = System.Drawing.Color.Empty;
            Panel3.Visible = true;
            GridView1.DataSource = ds;
            GridView1.DataBind();
            int i = ds.Tables[0].Rows.Count;
            int tot = 0;
            while (i > 0)
            {
                tot += (Convert.ToInt32(ds.Tables[0].Rows[i - 1][2].ToString()));
                i--;
            }
            LabelTotal.Text = tot.ToString();
            Label4.Visible = true;
            LabelTotal.Visible = true;
            string sel1 = "select dates,amount from tservice where dates>='" + from + "' and  dates<='" + to + "'";
            da = new SqlDataAdapter(sel1, con);
            da.Fill(ds);
            Chart1.DataSource = ds;
            Chart1.DataSource = ds;
            Chart1.Series["Series1"].XValueMember = "dates";
            Chart1.Series["Series1"].YValueMembers = "amount";
            Chart1.DataBind();
        }
        else
        {
            Page.ClientScript.RegisterStartupScript(GetType(), "msgtype()", "alert('No Data Available !!!')", true);
        }
    }
}