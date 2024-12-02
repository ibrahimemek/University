module machine_d (
    input wire x,
    input wire CLK,
    input wire RESET,
    output wire F,
    output wire [2:0] S
);
    wire a;
    wire b;
    wire c;
    wire d;
    wire e;
    wire f;

    assign d = (~a & b & ~x) | a;
    assign e = (a & b) | (b & x) | (~b & ~x);
    assign f = c ^ x;

    dff dff_a (.D(d), .CLK(CLK), .RESET(RESET), .Q(a));
    dff dff_b (.D(e), .CLK(CLK), .RESET(RESET), .Q(b));
    dff dff_c (.D(f), .CLK(CLK), .RESET(RESET), .Q(c));
    assign S = {a, b, c};
    assign F = a & b & ~c;
endmodule
