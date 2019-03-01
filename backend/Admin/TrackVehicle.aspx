<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="TrackVehicle.aspx.cs" Inherits="TrackVehicle" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
    <style type="text/css">
        .auto-style1 {
            width: 60%;
        }
        .auto-style2 {
            font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande", "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
            font-size: x-large;
        }
        .auto-style3 {
            font-family: "Calibri Light";
            font-size: x-large;
        }
        .auto-style4 {
            height: 23px;
        }
        .auto-style5 {
            font-family: calibri;
        }
        .auto-style6 {
            -webkit-border-radius: 10px;
            -moz-border-radius: 10px;
            background-color: White;
            width: 130px;
            height: 45px;
            color: #333333;
            outline: none;
            font-size: x-large;
            font-family: calibri;
        }
    .auto-style7 {
        font-family: "Calibri Light";
        font-size: xx-large;
    }
    </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <br />
            <strong>
            <asp:Label ID="Label7" CssClass="auto-style7" runat="server" Text="Track Vehicle"></asp:Label>
        </strong>
    <br />
    <table class="auto-style1">
        <tr>
            <td colspan="2" style="border-top-style: double; border-top-width: 4px; border-top-color: #C0C0C0">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">
                <asp:Label ID="LabelDid" runat="server" Visible="False"></asp:Label>
                <asp:Label ID="LabelVehNo" runat="server" Visible="False"></asp:Label>
            </td>
        </tr>
        <tr>
            <td width="30%">
                <asp:Label ID="Label1" runat="server" CssClass="auto-style2" Text="Driver Name"></asp:Label>
            </td>
            <td align="center" style="border-left-style: solid; border-left-width: 1px; border-left-color: #808080;" width="70%">
                <asp:DropDownList ID="DropDownList1" runat="server" AutoPostBack="True" CssClass="auto-style3" OnSelectedIndexChanged="DropDownList1_SelectedIndexChanged" Width="60%">
                </asp:DropDownList>
            </td>
        </tr>
        <tr>
            <td class="auto-style4"></td>
            <td align="center" class="auto-style4">
                <asp:Label ID="LabelErr" runat="server" CssClass="auto-style5" Text="Please select driver name !" Visible="False"></asp:Label>
            </td>
        </tr>
    </table>
    <asp:Label ID="lblMsg" runat="server" style="color: #666666; font-family: calibri; font-size: large" Visible="False"></asp:Label>
    <br />
    <br />
    <asp:Button ID="Button1" runat="server" CssClass="auto-style6" OnClick="Button1_Click" Text="Track Bus" Visible="False" Width="20%" />
    <br />
    <br />
    <br />
</asp:Content>

