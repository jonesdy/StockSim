DROP TABLE [Game];
DROP TABLE [Player];
DROP TABLE [Stock];
DROP TABLE [Transaction];
DROP TABLE [__MigrationHistory];
DROP TABLE [AspNetRoleClaims];
DROP TABLE [AspNetRoles];
DROP TABLE [AspNetUserClaims];
DROP TABLE [AspNetUserLogins];
DROP TABLE [AspNetUserRoles];
DROP TABLE [AspNetUsers];

CREATE TABLE [Game] (
  [Gid] integer PRIMARY KEY AUTOINCREMENT
, [Title] ntext NOT NULL
, [StartingMoney] decimal NOT NULL
, [Private] bit NOT NULL
, [StartTimestamp] datetime NOT NULL
);

CREATE TABLE [Player] (
  [Pid] integer PRIMARY KEY AUTOINCREMENT
, [Username] ntext NOT NULL
, [Gid] int NOT NULL
, [Balance] decimal NOT NULL
, [IsAdmin] bit NOT NULL
, [InviteCode] text NOT NULL
, [Enabled] bit NOT NULL
, FOREIGN KEY ([Gid]) REFERENCES Game([Gid])
);

CREATE TABLE [Stock] (
  [Sid] integer PRIMARY KEY AUTOINCREMENT
, [TickerSymbol] text NOT NULL
, [Pid] int NOT NULL
, [Count] int NOT NULL
, FOREIGN KEY ([Pid]) REFERENCES Player ([Pid])
);

CREATE TABLE [Transaction] (
  [Tid] integer PRIMARY KEY AUTOINCREMENT
, [Sid] int NOT NULL
, [Count] int NOT NULL
, [Price] decimal NOT NULL
, [Timestamp] datetime NOT NULL
, [Buy] bit NOT NULL
, FOREIGN KEY ([Sid]) REFERENCES Stock([Sid])
);

CREATE TABLE [__MigrationHistory] (
  [MigrationId] ntext NOT NULL
, [ContextKey] ntext NOT NULL
, [ProductVersion] ntext NOT NULL
, CONSTRAINT [PK_MigrationHistory] PRIMARY KEY ([MigrationId] ASC, [ContextKey] ASC)
);

CREATE TABLE [AspNetRoleClaims] (
  [Id] int identity NOT NULL
, [ClaimType] ntext NULL
, [ClaimValue] ntext NULL
, [RoleId] ntext NULL
, CONSTRAINT [PK_AspNetRoleClaims] PRIMARY KEY ([Id] ASC)
, CONSTRAINT [FK_AspNetRoleClaims_AspNetRoles_RoleId] FOREIGN KEY ([RoleId]) REFERENCES [AspNetRoles] ([Id])
);

CREATE TABLE [AspNetRoles] (
  [Id] ntext NOT NULL
, [ConcurrencyStamp] ntext NULL
, [Name] ntext NULL
, [NormalizedName] ntext NULL
, CONSTRAINT [PK_AspNetRoles] PRIMARY KEY ([Id] ASC)
);

CREATE TABLE [AspNetUserClaims] (
  [Id] int identity NOT NULL
, [ClaimType] ntext NULL
, [ClaimValue] ntext NULL
, [UserId] ntext NULL
, CONSTRAINT [PK_AspNetUserClaims] PRIMARY KEY ([Id] ASC)
, CONSTRAINT [FK_AspNetUserClaims_AspNetUsers_UserId] FOREIGN KEY ([UserId]) REFERENCES [AspNetUsers] ([Id])
);

CREATE TABLE [AspNetUserLogins] (
  [LoginProvider] ntext NOT NULL
, [ProviderKey] ntext NOT NULL
, [ProviderDisplayName] ntext NULL
, [UserId] ntext NULL
, CONSTRAINT [PK_AspNetUserLogins] PRIMARY KEY ([LoginProvider] ASC, [ProviderKey] ASC)
, CONSTRAINT [FK_AspNetUserLogins_AspNetUsers_UserId] FOREIGN KEY ([UserId]) REFERENCES [AspNetUsers] ([Id])
);

CREATE TABLE [AspNetUserRoles] (
  [UserId] ntext NOT NULL
, [RoleId] ntext NOT NULL
, CONSTRAINT [PK_AspNetUserRoles] PRIMARY KEY ([UserId] ASC, [RoleId] ASC)
, CONSTRAINT [FK_AspNetUserRoles_AspNetRoles_RoleId] FOREIGN KEY ([RoleId]) REFERENCES [AspNetRoles] ([Id])
, CONSTRAINT [FK_AspNetUserRoles_AspNetUsers_UserId] FOREIGN KEY ([UserId]) REFERENCES [AspNetUsers] ([Id])
);

CREATE TABLE [AspNetUsers] (
  [Id] ntext NOT NULL
, [AccessFailedCount] int NOT NULL
, [ConcurrencyStamp] ntext NULL
, [Email] ntext NULL
, [EmailConfirmed] bit NOT NULL
, [LockoutEnabled] bit NOT NULL
, [LockoutEnd] datetimeoffset  NULL
, [NormalizedEmail] ntext NULL
, [NormalizedUserName] ntext NULL
, [PasswordHash] ntext NULL
, [PhoneNumber] ntext NULL
, [PhoneNumberConfirmed] bit NOT NULL
, [SecurityStamp] ntext NULL
, [TwoFactorEnabled] bit NOT NULL
, [UserName] ntext NULL
, CONSTRAINT [PK_AspNetUsers] PRIMARY KEY ([Id] ASC)
);