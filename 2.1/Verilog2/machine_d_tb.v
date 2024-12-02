`timescale 1ns / 1ps

module machine_d_tb;
    reg x, CLK, RESET;
    wire F;
    wire [2:0] S;

    machine_d uut (
        .x(x),
        .CLK(CLK),
        .RESET(RESET),
        .F(F),
        .S(S)
    );

    initial begin
        x = 0;
        RESET = 0;
        CLK = 0;
	
	#10 x = 0;
	#10 RESET = 0;
        #10 CLK = 0;
        
        #10 x = 0;
        #10 RESET = 0;
        #10 CLK = 1;

        #10 x = 0;
        #10 RESET = 1;
	#10 CLK = 0;

        #10 x = 0;
        #10 RESET = 1;
        #10 CLK = 1;

	#10 x = 1;
	#10 RESET = 0;
        #10 CLK = 0;

        #10 x = 1;
        #10 RESET = 0;
        #10 CLK = 1;

        #10 x = 1;
        #10 RESET = 1;
        #10 CLK = 0;

        #10 x = 1;
        #10 RESET = 1;
        #10 CLK = 1;


        #10 $finish;
    end
endmodule