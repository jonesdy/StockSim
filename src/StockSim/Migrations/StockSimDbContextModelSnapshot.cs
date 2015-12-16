using System;
using Microsoft.Data.Entity;
using Microsoft.Data.Entity.Infrastructure;
using Microsoft.Data.Entity.Metadata;
using Microsoft.Data.Entity.Migrations;
using StockSim.Data.Access;

namespace StockSim.Migrations
{
    [DbContext(typeof(StockSimDbContext))]
    partial class StockSimDbContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.0-rc1-16348");

            modelBuilder.Entity("StockSim.Data.Transfer.GameDto", b =>
                {
                    b.Property<int>("Gid")
                        .ValueGeneratedOnAdd();

                    b.Property<bool>("Private");

                    b.Property<DateTime>("StartTimestamp");

                    b.Property<decimal>("StartingMoney");

                    b.Property<string>("Title");

                    b.HasKey("Gid");

                    b.HasAnnotation("Relational:TableName", "Game");
                });

            modelBuilder.Entity("StockSim.Data.Transfer.PlayerDto", b =>
                {
                    b.Property<int>("Pid")
                        .ValueGeneratedOnAdd();

                    b.Property<decimal>("Balance");

                    b.Property<bool>("Enabled");

                    b.Property<int>("Gid");

                    b.Property<string>("InviteCode");

                    b.Property<bool>("IsAdmin");

                    b.Property<string>("Username");

                    b.HasKey("Pid");

                    b.HasAnnotation("Relational:TableName", "Player");
                });

            modelBuilder.Entity("StockSim.Data.Transfer.StockDto", b =>
                {
                    b.Property<int>("Sid")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("Count");

                    b.Property<int>("Pid");

                    b.Property<string>("TickerSymbol");

                    b.HasKey("Sid");

                    b.HasAnnotation("Relational:TableName", "Stock");
                });

            modelBuilder.Entity("StockSim.Data.Transfer.TransactionDto", b =>
                {
                    b.Property<int>("Tid")
                        .ValueGeneratedOnAdd();

                    b.Property<bool>("Buy");

                    b.Property<int>("Count");

                    b.Property<decimal>("Price");

                    b.Property<int>("Sid");

                    b.Property<DateTime>("Timestamp");

                    b.HasKey("Tid");

                    b.HasAnnotation("Relational:TableName", "Transaction");
                });
        }
    }
}
