<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="Login.aspx.cs" Inherits="Login" %>

<asp:Content ID="Body" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div>
        <br />
            <strong>
            <asp:Label ID="Label1" CssClass="auto-style5" runat="server" Text="Login"></asp:Label>
        </strong>
        <br />
        <table class="auto-style2">
            <tr>
                <td colspan="2" style="border-top-style: double; border-top-width: 4px; border-top-color: #C0C0C0" width="30%">&nbsp;</td>
            </tr>
            <tr>
                <td align="right" width="20%">
                    <asp:Label ID="Label2" runat="server" CssClass="auto-style3" Text="Email ID :"></asp:Label>
                </td>
                <td align="center" width="80%">
                    <asp:TextBox ID="TextBox1" runat="server" Required="" CssClass="auto-style4" Height="35px" Width="60%"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td width="20%">&nbsp;</td>
                <td width="80%">&nbsp;</td>
            </tr>
            <tr>
                <td align="right" width="20%">
                    <asp:Label ID="Label3" runat="server" CssClass="auto-style3" Text="Password :"></asp:Label>
                </td>
                <td align="center" width="80%">
                    <asp:TextBox ID="TextBox2" runat="server" Required="" CssClass="auto-style4" Height="35px" TextMode="Password" Width="60%"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td width="20%">&nbsp;</td>
                <td width="80%">&nbsp;</td>
            </tr>
            <tr>
                <td align="center" class="auto-style8">
                    <br />
                </td>
                <td align="center" class="auto-style6" width="20%" style="width: 10%">
                    <asp:Button ID="Button1" runat="server" CssClass="auto-style7" Height="40px" OnClick="Button1_Click" Text="Login" Width="44%" />
                    <br />
                    <asp:LinkButton ID="LinkButton1" runat="server" PostBackUrl="~/UserRegistration.aspx" CssClass="auto-style4">Register Now</asp:LinkButton>
                </td>
            </tr>
        </table>
        <br />
        <br />
        <br />
    </div>
</asp:Content>
<asp:Content ID="Content1" runat="server" contentplaceholderid="head">
    <style type="text/css">
        .auto-style2 {
            width: 60%;
        }
    .auto-style3 {
        font-family: calibri;
        font-size: x-large;
        color: #333333;
    }
    .auto-style4 {
        font-family: calibri;
        font-size: large;
    }
    .auto-style5 {
        font-size: xx-large;
        font-family: "Calibri Light";
        text-decoration-color: gray;
        color: #333333;
    }
    .auto-style6 {
        width: 100%;
    }
    .auto-style7 {
        font-family: calibri;
        font-size: x-large;
    }
    .auto-style8 {
        width: 0%;
    }
    </style>
</asp:Content>
