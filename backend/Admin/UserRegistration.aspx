<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="UserRegistration.aspx.cs" Inherits="UserRegistration" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">
        <style type="text/css">
        .auto-style1 {
            font-family: calibri;
            font-size: large;
        }
        .auto-style2 {
            font-family: "Calibri Light";
            font-size: large;
        }
        .auto-style3 {
            font-size: large;
        }
        .auto-style6 {
            color: #FF0000;
            font-family: "Calibri Light";
        }
        .auto-style7 {
            font-family: "Calibri Light";
            text-decoration: underline;
        }
        .auto-style8 {
            font-family: calibri;
            font-size: medium;
            color: #333333;
        }
            .auto-style9 {
                font-size: large;
                font-family: calibri;
            }
    .auto-style5 {
        font-size: xx-large;
        font-family: "Calibri Light";
        text-decoration-color: gray;
        color: #333333;
    }
    </style>
     <script type="text/javascript">
         function ClientValidate(source, clientside_arguments) {
             //Test whether the length of the value is more than 6 characters
             if (clientside_arguments.Value.length >= 6) {
                 clientside_arguments.IsValid = true;
             }
             else { clientside_arguments.IsValid = false };
         }
</script>
<script type="text/javascript">

    function CheckPasswordStrength(password) {
        var password_strength = document.getElementById("password_strength");

        //TextBox left blank.
        if (password.length == 0) {
            password_strength.innerHTML = "";
            return;
        }

        //Regular Expressions.
        var regex = new Array();
        regex.push("[A-Z]"); //Uppercase Alphabet.
        regex.push("[a-z]"); //Lowercase Alphabet.
        regex.push("[0-9]"); //Digit.
        regex.push("[$@$!%*#?&]"); //Special Character.

        var passed = 0;

        //Validate for each Regular Expression.
        for (var i = 0; i < regex.length; i++) {
            if (new RegExp(regex[i]).test(password)) {
                passed++;
            }
        }

        //Validate for length of Password.
        if (passed > 2 && password.length > 8) {
            passed++;
        }

        //Display status.
        var color = "";
        var strength = "";
        switch (passed) {
            case 0:

            case 1:
                strength = "Weak";
                color = "red";
                break;
            case 2:
                strength = "Good";
                color = "darkorange";
                break;
            case 3:
            case 4:
                strength = "Strong";
                color = "green";
                break;
            case 5:
                strength = "Very Strong";
                color = "darkgreen";
                break;
        }
        password_strength.innerHTML = strength;
        password_strength.style.color = color;
    }
</script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
        <br />
            <strong>
            <asp:Label ID="Label11" CssClass="auto-style5" runat="server" Text="Registration"></asp:Label>
        </strong>
    <br />
    <table width="90%">
        <tr>
            <td align="right" width="30%">&nbsp;</td>
            <td align="center" width="40%" colspan="2" style="border-top-style: double; border-top-width: 4px; border-top-color: #C0C0C0">
                &nbsp;</td>
            <td align="center" width="30%">
                &nbsp;</td>
        </tr>
        <tr>
            <td align="right" width="30%"><strong>
                <asp:Label ID="Label1" runat="server" CssClass="auto-style2" Text="Name :"></asp:Label>
                </strong></td>
            <td align="center" width="40%" colspan="2">
                <asp:TextBox ID="TextBox1" runat="server" Required="" Placeholder=" Your Name...." CssClass="auto-style1" Height="35px" Width="70%"></asp:TextBox>
                <br />
            </td>
            <td align="center" width="30%">
                <asp:Label ID="LabelId" runat="server" Visible="False"></asp:Label>
            </td>
        </tr>
        <tr>
            <td align="right" width="30%">&nbsp;</td>
            <td align="center" width="40%" colspan="2">
                &nbsp;</td>
            <td align="center" width="20%">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label2" runat="server" CssClass="auto-style2" Text="Contact No. :"></asp:Label>
                </strong></td>
            <td align="center" colspan="2">
                <asp:TextBox ID="TextBox2" runat="server" Required="" Placeholder=" Your Contact No...." CssClass="auto-style1" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="2">
                <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="TextBox2" CssClass="auto-style4" ErrorMessage="Invalid Contact No." style="font-family: calibri; color: #FF3300" ValidationExpression="^[7-9]\d{9}$"></asp:RegularExpressionValidator>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label3" runat="server" CssClass="auto-style2" Text="Email ID :"></asp:Label>
                </strong></td>
            <td align="center" colspan="2">
                <asp:TextBox ID="TextBox3" runat="server" CssClass="auto-style1" Required="" Placeholder=" Your Email ID...." Height="35px" Width="70%"></asp:TextBox>
                <br />
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="2">
                <strong>
                <asp:Label ID="LabelErr" runat="server" CssClass="auto-style6" Text="Email Id already exist !" Visible="False"></asp:Label>
                </strong>
                <asp:RegularExpressionValidator ID="RegularExpressionValidator2" runat="server" ControlToValidate="TextBox3" CssClass="auto-style4" ErrorMessage="Invalid Email Id" style="font-family: calibri; color: #FF3300" ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"></asp:RegularExpressionValidator>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label4" runat="server" CssClass="auto-style2" Text="Age :"></asp:Label>
                </strong></td>
            <td align="center" colspan="2">
                <asp:TextBox ID="TextBox4" runat="server" Required="" Placeholder=" Your Age...." CssClass="auto-style1" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="2">
                &nbsp;</td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label10" runat="server" CssClass="auto-style2" Text="Vehicle No. :"></asp:Label>
                </strong></td>
            <td align="center" colspan="2">
                <asp:TextBox ID="TextBox5" runat="server" Required="" Placeholder=" Your Contact No...." CssClass="auto-style1" Height="35px" Width="70%"></asp:TextBox>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="2">
                &nbsp;</td>
            <td align="left"><strong>
                <asp:Label ID="Label7" runat="server" CssClass="auto-style7" Text="Password Combinations**:" Font-Underline="False"></asp:Label>
                </strong>
                </td>
        </tr>
        <tr>
            <td align="right"><strong>
                <asp:Label ID="Label5" runat="server" CssClass="auto-style2" Text="Password :"></asp:Label>
                </strong></td>
            <td align="center" style="border-right-style: solid; border-right-width: 1px; border-right-color: #808080" colspan="2">
            <input id="Password1" style="width:70%; height:35px" runat="server" onkeypress="CheckPasswordStrength(this.value)" placeholder=" Enter Password...." required="" type="password" class="auto-style9" />   
            </td>
            <td align="left" valign="top" style="border-top-style: solid; border-top-width: 1px; border-top-color: #808080">-
                <asp:Label ID="Label8" runat="server" CssClass="auto-style8" Text="Min. 6 charaters with no blank spaces"></asp:Label>
                <br />
                -
                <asp:Label ID="Label9" runat="server" CssClass="auto-style8" Text="Include Special characters (!@#$)"></asp:Label>
            </td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" width="17%">
                <span id="password_strength" style=" font-family:Calibri;"></span></td>
            <td align="left" style="50%">
                <asp:CustomValidator ID="CustomValidator1" runat="server" ClientValidationFunction="ClientValidate" ControlToValidate="Password1" ErrorMessage="* Minimum 6 characters" Font-Size="Small" ForeColor="Red" style="font-family: Calibri; font-size: medium"></asp:CustomValidator>
            </td>
            <td align="center">&nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="2">
                &nbsp;</td>
            <td align="center">
                &nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="2">
                <asp:Button ID="Button1" runat="server" CssClass="auto-style3" Text="Submit" OnClick="Button1_Click" />
            </td>
            <td align="center">
                &nbsp;</td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="center" colspan="3">&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td align="center" colspan="3">&nbsp;</td>
        </tr>
    </table>
</asp:Content>

