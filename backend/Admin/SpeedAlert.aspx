<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="SpeedAlert.aspx.cs" Inherits="SpeedAlert" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
     <style type="text/css">
        .auto-style1 {
            font-size: large;
            font-family: calibri;
        }
    .auto-style9 {
        font-family: "Calibri Light";
        font-size: xx-large;
    }
    </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
     <br />
    <strong>
            <asp:Label ID="Label8" CssClass="auto-style9" runat="server" Text="Driver Details"></asp:Label>
        </strong>
<br />
    <br />
    <asp:GridView ID="GridView1" runat="server" CellPadding="4" CssClass="auto-style1" ForeColor="Black" GridLines="Vertical" AutoGenerateColumns="False" Width="80%" BackColor="White" BorderColor="#DEDFDE" BorderStyle="None" BorderWidth="1px" OnRowCommand="GridView1_RowCommand">
        <AlternatingRowStyle BackColor="White" />
        <FooterStyle BackColor="#CCCC99" />
        <HeaderStyle BackColor="#6B696B" Font-Bold="True" ForeColor="White" />
        <PagerStyle BackColor="#F7F7DE" ForeColor="Black" HorizontalAlign="Right" />
        <RowStyle BackColor="#F7F7DE" />
        <SelectedRowStyle BackColor="#CE5D5A" Font-Bold="True" ForeColor="White" />
        <SortedAscendingCellStyle BackColor="#FBFBF2" />
        <SortedAscendingHeaderStyle BackColor="#848384" />
        <SortedDescendingCellStyle BackColor="#EAEAD3" />
        <SortedDescendingHeaderStyle BackColor="#575357" />
        <Columns>
            <asp:BoundField HeaderText="Driver ID" DataField="DId" />
            <asp:BoundField HeaderText="Driver Name" DataField="Name" />
            <asp:BoundField HeaderText="Contact No." DataField="Contact" />
            <asp:BoundField HeaderText="Location" DataField="latlng" />
            <asp:BoundField HeaderText="Speed" DataField="Speed" />
            <asp:BoundField HeaderText="Speed Limit" DataField="Speed1" />
            <asp:BoundField HeaderText="Date" DataField="SDate" />
            <asp:BoundField HeaderText="Time" DataField="STime" />
            <asp:TemplateField HeaderText ="Delete">
            <ItemTemplate>
                <asp:LinkButton ID="Delete" runat ="server" Font-Underline="true" ForeColor="Black" CommandArgument='<%#Eval("DId")+"*"+Eval("SDate")+"*"+Eval("STime")%>' Text ="Delete" CommandName ="Delete"></asp:LinkButton>
            </ItemTemplate>
          </asp:TemplateField>
        </Columns>
    </asp:GridView>
     <br />
     <br />
    <asp:Label ID="lblMsg" runat="server" style="color: #666666; font-family: calibri; font-size: large" Visible="False"></asp:Label>
    <br />
    <br />
</asp:Content>