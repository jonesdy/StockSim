using Microsoft.Data.Entity.Migrations;

namespace StockSim.Migrations
{
    public partial class GameOfficial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "Official",
                table: "Game",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(name: "Official", table: "Game");
        }
    }
}
