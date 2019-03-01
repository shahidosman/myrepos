<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="AddDriver.aspx.cs" Inherits="AddDriver" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
    <style type="text/css">

        .auto-style2 {
            font-family: "Calibri Light";
            font-size: large;
        }
        .auto-style6 {
            color: #FF0000;
            font-family: "Calibri Light";
        }
        .auto-style3 {
            font-size: large;
        }
        .auto-style7 {
            font-family: calibri;
            font-size: large;
        }
    .auto-style5 {
        font-size: xx-large;
        font-family: "Calibri Light";
        text-decoration-color: gray;
        color: #333333;
    }
    </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <strong>
            <br />
            <asp:Label ID="Label7" CssClass="auto-style5" runat="server" Text="Driver Registration"></asp:Label>
        </strong>
<br />
    <table width="90%">
        <tr>
            <td align="right" width="30%">&nbsp;</td>
            <td align="center" width="40%" style="border-top-style: double; border-top-width: 4px; border-top-color: #C0C0C0">
                &nbsp;</td>
            <td align="center" width="30%">
                &nbsp;</td>
        </tr>
        <tr>
            <td align="right" width="30%"><strong>
                <asp:Label ID="Label1" runat="server" CssClass="auto-style2" Text="Name :"></asp:Label>
                </strong></td>
            <td align="center" width="40%">
                <asp:TextBox ID="TextBox1" runat="server" Required="" Placeholder=" Driver's Name...." CssClass="auto-style7" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center" width="30%">
                <asp:Label ID="LabelId" runat="server" Visible="False"></asp:Label>
            </td>
        </tr>
        <tr>
            <td align="right" width="30%">&nbsp;</td>
            <td align="center" width="40%">&nbsp;</td>
            <td align="center" width="20%">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label2" runat="server" CssClass="auto-style2" Text="Contact No. :"></asp:Label>
                </strong></td>
            <td align="center">
                <asp:TextBox ID="TextBox2" runat="server" Required="" Placeholder=" Driver's Contact No...." CssClass="auto-style7" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center"><strong>
                <asp:Label ID="LabelErr" runat="server" CssClass="auto-style6" Text="Contact No. already exist !" Visible="False"></asp:Label>
&nbsp;</strong><asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="TextBox2" CssClass="auto-style4" ErrorMessage="Invalid Contact No." style="font-family: calibri; color: #FF3300" ValidationExpression="^[7-9]\d{9}$"></asp:RegularExpressionValidator>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label3" runat="server" CssClass="auto-style2" Text="Address :"></asp:Label>
                </strong></td>
            <td align="center">
                <asp:TextBox ID="TextBox3" runat="server" CssClass="auto-style7" Required="" Placeholder=" Driver's Address...." Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label4" runat="server" CssClass="auto-style2" Text="Age :"></asp:Label>
                </strong></td>
            <td align="center">
                <asp:TextBox ID="TextBox4" runat="server" Required="" Placeholder=" Driver's Age...." CssClass="auto-style7" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="left">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label5" runat="server" CssClass="auto-style2" Text="Vehicle No. :"></asp:Label>
                </strong></td>
            <td align="center">&nbsp;<asp:TextBox ID="TextBox5" runat="server" Required="" Placeholder=" Vehicle No....." CssClass="auto-style7" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="left" valign="top">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" width="17%"><span id="password_strength" style=" font-family:Calibri;"></span></td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label6" runat="server" CssClass="auto-style2" Text="Salary :"></asp:Label>
                </strong></td>
            <td align="center" width="17%">
                <asp:TextBox ID="TextBox6" runat="server" Required="" Placeholder=" Driver's Salary...." CssClass="auto-style7" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label8" runat="server" CssClass="auto-style2" Text="Speed Limit :"></asp:Label>
                </strong></td>
            <td align="center" width="17%">
                <asp:TextBox ID="TextBox7" runat="server" Required="" Placeholder=" Driver's Salary...." CssClass="auto-style7" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center">
                <asp:Button ID="Button1" runat="server" CssClass="auto-style3" Text="Submit" OnClick="Button1_Click1"/>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td align="center" colspan="2">&nbsp;</td>
        </tr>
    </table>
    <br />
</asp:Content>

