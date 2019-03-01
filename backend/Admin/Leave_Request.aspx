<%@ Page Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="Leave_Request.aspx.cs" Inherits="Leave_Request" %>

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
            <asp:Label ID="Label8" CssClass="auto-style9" runat="server" Text="Driver Leave Application Details"></asp:Label>
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
            <asp:BoundField HeaderText="Leave ID" DataField="LId" />
            <asp:BoundField HeaderText="Driver Name" DataField="DName" />
            <asp:BoundField HeaderText="Contact No." DataField="Contact" />
            <asp:BoundField HeaderText="Start Date" DataField="sdate" />
            <asp:BoundField HeaderText="End Date" DataField="edate" />
            <asp:BoundField HeaderText="Note / Reason" DataField="reason" />
            <asp:BoundField HeaderText="Applied Date" DataField="dt" />
            <asp:BoundField HeaderText="Status" DataField="Status" />
            <asp:TemplateField HeaderText ="Action">
            <ItemTemplate>
                <asp:LinkButton ID="Action" runat ="server" Font-Underline="true" ForeColor="Black" CommandArgument='<%#Eval("LId")%>' Text ="Action" CommandName ="Action" ></asp:LinkButton>
            </ItemTemplate>
          </asp:TemplateField>
        </Columns>
    </asp:GridView>
     <br />
     <asp:Panel ID="Panel3" runat="server" Visible="False" Width="80%">
         <br />
         <asp:Label ID="Label9" runat="server" Font-Names="calibri" Font-Size="Large" Text="Application Status :"></asp:Label>
         &nbsp;<asp:DropDownList ID="DropDownList1" runat="server" Font-Names="Calibri" Font-Size="Large" Height="40px" Width="145px">
             <asp:ListItem>Accept</asp:ListItem>
             <asp:ListItem>Decline</asp:ListItem>
         </asp:DropDownList>
         <br />
         <asp:Label ID="lblLId" runat="server" Visible="False"></asp:Label>
         <br />
         <asp:Button ID="Button1" runat="server" BackColor="#484848" Font-Names="Bell MT" Font-Size="Medium" ForeColor="White" Height="40px" OnClick="Button1_Click" Text="SUBMIT" Width="140px" />
         <br />
     </asp:Panel>
     <br />
     <br />
    <asp:Label ID="lblMsg" runat="server" style="color: #666666; font-family: calibri; font-size: large" Visible="False"></asp:Label>
    <br />
    <br />
</asp:Content>