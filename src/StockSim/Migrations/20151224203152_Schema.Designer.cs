using System;
using Microsoft.Data.Entity;
using Microsoft.Data.Entity.Infrastructure;
using Microsoft.Data.Entity.Metadata;
using Microsoft.Data.Entity.Migrations;
using StockSim.Data.Access;

namespace StockSim.Migrations
{
    [DbContext(typeof(StockSimDbContext))]
    [Migration("20151224203152_Schema")]
    partial class Schema
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.0-rc1-16348");

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityRole", b =>
                {
                    b.Property<string>("Id");

                    b.Property<string>("ConcurrencyStamp")
                        .IsConcurrencyToken();

                    b.Property<string>("Name")
                        .HasAnnotation("MaxLength", 256);

                    b.Property<string>("NormalizedName")
                        .HasAnnotation("MaxLength", 256);

                    b.HasKey("Id");

                    b.HasIndex("NormalizedName")
                        .HasAnnotation("Relational:Name", "RoleNameIndex");

                    b.HasAnnotation("Relational:TableName", "AspNetRoles");
                });

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityRoleClaim<string>", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ClaimType");

                    b.Property<string>("ClaimValue");

                    b.Property<string>("RoleId")
                        .IsRequired();

                    b.HasKey("Id");

                    b.HasAnnotation("Relational:TableName", "AspNetRoleClaims");
                });

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityUserClaim<string>", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ClaimType");

                    b.Property<string>("ClaimValue");

                    b.Property<string>("UserId")
                        .IsRequired();

                    b.HasKey("Id");

                    b.HasAnnotation("Relational:TableName", "AspNetUserClaims");
                });

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityUserLogin<string>", b =>
                {
                    b.Property<string>("LoginProvider");

                    b.Property<string>("ProviderKey");

                    b.Property<string>("ProviderDisplayName");

                    b.Property<string>("UserId")
                        .IsRequired();

                    b.HasKey("LoginProvider", "ProviderKey");

                    b.HasAnnotation("Relational:TableName", "AspNetUserLogins");
                });

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityUserRole<string>", b =>
                {
                    b.Property<string>("UserId");

                    b.Property<string>("RoleId");

                    b.HasKey("UserId", "RoleId");

                    b.HasAnnotation("Relational:TableName", "AspNetUserRoles");
                });

            modelBuilder.Entity("StockSim.Data.Transfer.ApplicationUser", b =>
                {
                    b.Property<string>("Id");

                    b.Property<int>("AccessFailedCount");

                    b.Property<string>("ConcurrencyStamp")
                        .IsConcurrencyToken();

                    b.Property<string>("Email")
                        .HasAnnotation("MaxLength", 256);

                    b.Property<bool>("EmailConfirmed");

                    b.Property<bool>("LockoutEnabled");

                    b.Property<DateTimeOffset?>("LockoutEnd");

                    b.Property<string>("NormalizedEmail")
                        .HasAnnotation("MaxLength", 256);

                    b.Property<string>("NormalizedUserName")
                        .HasAnnotation("MaxLength", 256);

                    b.Property<string>("PasswordHash");

                    b.Property<string>("PhoneNumber");

                    b.Property<bool>("PhoneNumberConfirmed");

                    b.Property<string>("SecurityStamp");

                    b.Property<bool>("TwoFactorEnabled");

                    b.Property<string>("UserName")
                        .HasAnnotation("MaxLength", 256);

                    b.HasKey("Id");

                    b.HasIndex("NormalizedEmail")
                        .HasAnnotation("Relational:Name", "EmailIndex");

                    b.HasIndex("NormalizedUserName")
                        .HasAnnotation("Relational:Name", "UserNameIndex");

                    b.HasAnnotation("Relational:TableName", "AspNetUsers");
                });

            modelBuilder.Entity("StockSim.Data.Transfer.ClosedTimeDto", b =>
                {
                    b.Property<int>("Cid")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ClosedReason");

                    b.Property<DateTime>("EndTime");

                    b.Property<DateTime>("StartTime");

                    b.HasKey("Cid");

                    b.HasAnnotation("Relational:TableName", "ClosedTime");
                });

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

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityRoleClaim<string>", b =>
                {
                    b.HasOne("Microsoft.AspNet.Identity.EntityFramework.IdentityRole")
                        .WithMany()
                        .HasForeignKey("RoleId");
                });

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityUserClaim<string>", b =>
                {
                    b.HasOne("StockSim.Data.Transfer.ApplicationUser")
                        .WithMany()
                        .HasForeignKey("UserId");
                });

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityUserLogin<string>", b =>
                {
                    b.HasOne("StockSim.Data.Transfer.ApplicationUser")
                        .WithMany()
                        .HasForeignKey("UserId");
                });

            modelBuilder.Entity("Microsoft.AspNet.Identity.EntityFramework.IdentityUserRole<string>", b =>
                {
                    b.HasOne("Microsoft.AspNet.Identity.EntityFramework.IdentityRole")
                        .WithMany()
                        .HasForeignKey("RoleId");

                    b.HasOne("StockSim.Data.Transfer.ApplicationUser")
                        .WithMany()
                        .HasForeignKey("UserId");
                });
        }
    }
}
